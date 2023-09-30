package com.devh.cafe.api.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = [
        "com.devh.cafe.infrastructure",
        "com.devh.cafe.utility",
        "com.devh.cafe.api",
    ]
)
@EnableJpaRepositories
@EntityScan(
    basePackages = [
        "com.devh.cafe.infrastructure.database",
    ]
)
class MemberApiApplication

fun main(args: Array<String>) {
    runApplication<MemberApiApplication>(*args)
}