package com.example.jpa.repository;

import com.example.jpa.entity.User;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByProfession(String profession);

    long countByAge(int age);


    // multi condition
    List<User> findByProfessionAndAge(String profession, int age);

    // ignore case
    List<User> findByProfessionIgnoreCase(String profession);

    @Query("select u from User u")
    List<User> getUsersCustomQuery();
// ---------- DELETE CASES ----------

    //Always add @Transactional for DELETE/UPDATE JPQL AND INSERT NATIVE QUERY:
    //You must add @Transactional, because: DELETE/UPDATE changes DB state.
    //Spring requires a transaction active to execute write operations.
    //Otherwise, you get: javax.persistence.TransactionRequiredException: Executing an update/delete query

    // Derived delete query (Spring auto handles it)
    //long deleteByName(String name);

    List<User> deleteByName(String name);

    // Custom JPQL delete
    @Transactional
    @Modifying
    @Query("delete from User u where u.age = :age")
    int deleteUsersByAge(@Param("age") int age);

    // ---------- UPDATE CASES ----------

    // Update single field
    @Transactional
    @Modifying
    @Query("update User u set u.profession = :profession where u.id = :id")
    int updateUserProfession(@Param("id") Integer id, @Param("profession") String profession);

    // Update multiple fields
    @Transactional
    @Modifying
    @Query("update User u set u.name = :name, u.age = :age where u.id = :id")
    int updateUserNameAndAge(@Param("id") Integer id, @Param("name") String name, @Param("age") int age);

    //JPQL does NOT support INSERT (only SELECT, UPDATE, DELETE).

    // ------------------------------------- NATIVE QUERY CASES -------------------------------------

    // Native delete
    @Transactional
    @Modifying
    @Query(value = "delete from users where profession = :profession", nativeQuery = true)
    int deleteByProfessionNative(@Param("profession") String profession);

    // Native update
    @Transactional
    @Modifying
    @Query(value = "update users set age = :age where name = :name", nativeQuery = true)
    int updateAgeByNameNative(@Param("age") int age, @Param("name") String name);

    //Insert Using @Modifying + Native Query. JPQL does NOT support INSERT (only SELECT, UPDATE, DELETE).
    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO users (name, age, profession)
            VALUES (:name, :age, :profession)
            """, nativeQuery = true)
    int insertUser(@Param("name") String name, @Param("age") int age, @Param("profession") String profession);

    //Bulk Insert (Native)
    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO users (name, age, profession)
            SELECT name, age, profession
            FROM temp_users
            """, nativeQuery = true)
    int bulkInsertFromTemp();

}