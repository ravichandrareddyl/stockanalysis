package com.stocks.analysis.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfig {
    /* Need to figure out the impact by using only two channels.*/

    @Bean
    public MessageChannel bavChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel oldBavChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel jobListenerChannel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public MessageChannel routingChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel errorChannel() {
        return MessageChannels.direct().get();
    }
    
}