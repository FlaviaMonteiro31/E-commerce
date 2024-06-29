package br.com.ms_gestao_itens.cargaMassiva;

import br.com.ms_gestao_itens.model.Produto;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
//@Conditional(CondicaoParaExecutar.class)
@EnableScheduling
public class BatchConfiguration {

    @Bean
    public Job processarArquivo(JobRepository jobRepository, Step step){
        return new JobBuilder("CargaDeProdutos",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                        PlatformTransactionManager platformTransactionManager ,
                        ItemReader<Produto> reader,
                        ItemProcessor<Produto, Produto> processor,
                        ItemWriter<Produto> writer
                        ){
        return new StepBuilder("step",jobRepository)
                .<Produto,Produto>chunk(20,platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Produto> reader(){
        BeanWrapperFieldSetMapper<Produto> fiedSetMapper = new BeanWrapperFieldSetMapper<>();
        fiedSetMapper.setTargetType(Produto.class);
        return new FlatFileItemReaderBuilder<Produto>()
                .name("readerProduto")
                .resource(new ClassPathResource("produtos.csv"))
                .delimited()
                .names("nome","descricao","tamanho","cor","preco","quantidade")
                .fieldSetMapper(fiedSetMapper)
                .build();
    }

    @Bean
    public ItemWriter<Produto> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Produto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("insert into tb_produto "
                    + "(id,nome,descricao,tamanho,cor,preco,quantidade)"
                    + "values (:id,:nome,:descricao,:tamanho,:cor,:preco,:quantidade)")
                .build();
    }

    @Bean
    public ItemProcessor<Produto, Produto> processor(){
        return new ProdutoProcessor();
    }

}
