/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mpk.client.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 */
@SpringBootConfiguration
@ComponentScan(basePackages = "ru.mpk.client")
@EnableScheduling
public class SpringConfiguration {
}
