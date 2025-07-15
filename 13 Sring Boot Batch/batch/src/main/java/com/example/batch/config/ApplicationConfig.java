
// Spring Batch configuration for reading Person data from MongoDB Atlas (db: Person, collection: bank_users),
// sending emails for Pending status, and writing output to XML.
package com.example.batch.config;

import com.example.batch.model.Person; // Domain model for MongoDB documents
import com.example.batch.util.MailUtil; // Utility for sending emails

import lombok.RequiredArgsConstructor; // For constructor injection

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job; // Spring Batch Job
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step; // Spring Batch Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder; // Job builder
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository; // Job repository for metadata
import org.springframework.batch.core.step.builder.StepBuilder; // Step builder
import org.springframework.batch.item.ItemProcessor; // Processor for Person items
import org.springframework.batch.item.data.MongoPagingItemReader; // Reader for MongoDB
import org.springframework.batch.item.xml.StaxEventItemWriter; // Writer for XML output
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource; // For XML file output
import org.springframework.data.domain.Sort.Direction; // For sorting MongoDB results
import org.springframework.data.mongodb.core.MongoTemplate; // MongoDB template
import org.springframework.oxm.xstream.XStreamMarshaller; // XML marshaller
import org.springframework.transaction.PlatformTransactionManager; // Transaction manager

// Main configuration class for Spring Batch job
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ApplicationConfig {

    // Injected dependencies
    private final JobRepository jobRepository; // For batch metadata
    private final PlatformTransactionManager transactionManager; // For chunk transactions
    private final MongoTemplate template; // For MongoDB operations
    private final MailUtil util; // For sending emails

    /**
     * MongoPagingItemReader bean for reading Person documents from MongoDB Atlas.
     * Database: Person
     * Collection: bank_users
     * Fetches all records, sorted by id descending.
     */
    @Bean
    public MongoPagingItemReader<Person> reader() {
        MongoPagingItemReader<Person> reader = new MongoPagingItemReader<>();
        reader.setTemplate(template);
        reader.setCollection("bank_users");
        reader.setQuery("{}");
        reader.setTargetType(Person.class);
        reader.setSort(new HashMap<String, Direction>() {
            {
                put("id", Direction.DESC);
            }
        });
        reader.setPageSize(10);
        try {
            reader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(template.findAll(Person.class, "bank_users"));
        return reader;
    }

    /**
     * StaxEventItemWriter bean for writing Person objects to XML file.
     * Output file: xml/bank.xml
     * Root tag: Person
     */
    @Bean
    public StaxEventItemWriter<Person> writer() {
        StaxEventItemWriter<Person> writer = new StaxEventItemWriter<>();
        writer.setRootTagName("Persons"); // Root XML tag  (should be plural for multiple records)
        writer.setResource(new FileSystemResource("xml/bank.xml")); // Output file
        writer.setMarshaller(marshaller()); // XML marshaller
        return writer;
    }

    /**
     * XStreamMarshaller for converting Person objects to XML.
     */
    private XStreamMarshaller marshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class<?>> map = new HashMap<>();
        map.put("Person", Person.class); // Alias for XML
        marshaller.setAliases(map);
        return marshaller;
    }

    /**
     * ItemProcessor bean for processing Person objects.
     * Sends email for Pending status and returns the Person for writing to XML.
     */
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return person -> {
            // Only process if status is Pending
            if (person.getStatus().equals("Pending")) {
                String msg = util.sendEmail(person.getEmail(), buildMessage(person)); // Send email
                System.out.println(msg); // Log result
                return person; // Write to XML
            }
            return null; // Skip non-pending
        };
    }

    /**
     * Helper method to build the email message body for a Person.
     */
    private String buildMessage(Person person) {
        return "Dear " + person.getName() + "," + "\n"
                + "Statement of your credit card ending with " + person.hashCode() + "** has been generated.\n"
                + "Dues amount: " + person.getOutstandingAmount() + "\n"
                + "Last payment date: "
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a").format(person.getLastDueDate()) + "\n\n"
                + "If you already paid, please ignore this email.\n"
                + "Thanks for using our credit card.";
    }

    /**
     * Step bean for Spring Batch job.
     * Reads from MongoDB, processes Pending users, writes to XML.
     */
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    /**
     * Job bean for Spring Batch.
     * Runs the step to process and send emails for Pending users.
     */
    @Bean
    public Job runJob() {
        return new JobBuilder("report generation", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public CommandLineRunner startJob(JobLauncher jobLauncher, Job runJob) {
        return args -> {
            System.out.println("Launching Spring Batch job...");
            jobLauncher.run(runJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // unique params to avoid restart issue
                    .toJobParameters());
        };
    }
}
