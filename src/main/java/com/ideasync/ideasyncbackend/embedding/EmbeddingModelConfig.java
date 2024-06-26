package com.ideasync.ideasyncbackend.embedding;

import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingModelConfig {

    @Bean
    public TransformersEmbeddingModel embeddingModel() {
        return new TransformersEmbeddingModel();
    }




}
