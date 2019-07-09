package com.charter.springBatch.config;

import com.charter.springBatch.model.Student;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class Step1 {
    @Bean
    public FlatFileItemReader<Student> fileReader(@Value("${input}") Resource in) throws Exception {

        return new FlatFileItemReaderBuilder<Student>()
                .name("file-reader")
                .resource(in)
                .targetType(Student.class)
                .linesToSkip(1)
                .delimited().delimiter(",").names(new String[]{"firstName", "lastName",  "email", "age"})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Student> jdbcWriter(DataSource ds) {
        return new JdbcBatchItemWriterBuilder<Student>()
                .dataSource(ds)
                .sql("insert into STUDENT( FIRST_NAME, LAST_NAME, EMAIL, AGE) values (:firstName, :lastName, :email, :age)")
                .beanMapped()
                .build();
    }
}
