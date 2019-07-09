package com.charter.springBatch.config;

import com.charter.springBatch.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class Batch {
    @Bean
    Job job(JobBuilderFactory jbf,
            StepBuilderFactory sbf,
            Step1 step1,
            Step2 step2) throws Exception {

        Step s1 = sbf.get("file-db")
                .<Student, Student>chunk(100)
                .reader(step1.fileReader(null))
                .writer(step1.jdbcWriter(null))
                .build();

        Step s2 = sbf.get("db-file")
                .<Map<Integer, Integer>, Map<Integer, Integer>>chunk(100)
                .reader(step2.jdbcReader(null))
                .writer(step2.fileWriter(null))
                .build();

        return jbf.get("etl")
                .incrementer(new RunIdIncrementer())
                .start(s1)
                .next(s2)
                .build();

    }
}
