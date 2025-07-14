package com.example.batch.config;

import com.example.batch.model.Person;
import com.example.batch.util.MailUtil;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MongoTemplate template;
    private final MailUtil util;

    @Bean
    public MongoPagingItemReader<Person> reader() {
        MongoPagingItemReader<Person> reader = new MongoPagingItemReader<>();
        reader.setTemplate(template);
        reader.setQuery("{}"); // fetch all records
        reader.setTargetType(Person.class);
        reader.setSort(new HashMap<String, Direction>() {{
            put("_custId", Direction.DESC);
        }});
        return reader;
    }

    @Bean
    public StaxEventItemWriter<Person> writer() {
        StaxEventItemWriter<Person> writer = new StaxEventItemWriter<>();
        writer.setRootTagName("Persons");
        writer.setResource(new FileSystemResource("xml/bank.xml"));
        writer.setMarshaller(marshaller());
        return writer;
    }

    private XStreamMarshaller marshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class<?>> map = new HashMap<>();
        map.put("Person", Person.class);
        marshaller.setAliases(map);
        return marshaller;
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return person -> {
            if (person.getStatus().equalsIgnoreCase("Pending")) {
                String msg = util.sendEmail(person.getEmail(), buildMessage(person));
                System.out.println(msg);
                return person;
            }
            return null;
        };
    }

    private String buildMessage(Person person) {
        return "Dear " + person.getName() + "," + "\n"
                + "Statement of your credit card ending with " + person.hashCode() + "** has been generated.\n"
                + "Dues amount: " + person.getOutstandingAmount() + "\n"
                + "Last payment date: "
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a").format(person.getLastDueDate()) + "\n\n"
                + "If you already paid, please ignore this email.\n"
                + "Thanks for using our credit card.";
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("report generation", jobRepository)
                .start(step1())
                .build();
    }
}
