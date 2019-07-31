/**
 * Copyright © 2018 spring-data-dynamodb-example (https://github.com/derjust/spring-data-dynamodb-examples)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.derjust.spring_data_dynamodb_examples.custom;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@SuppressWarnings("unused") // This class is used as per
							// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations
public class CustomUserRepositoryMethodsImpl implements CustomUserRepositoryMethods {

	private final DynamoDBMapper mapper;

	@Autowired
	public CustomUserRepositoryMethodsImpl(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public User calculateAge(User user) {
		// Just some javax.time mumbo-jumbo
		Instant birthday = user.getBirthday();
		LocalDate now = LocalDate.now();
		Period age = Period.between(LocalDate.ofInstant(birthday, ZoneId.systemDefault()), now);

		user.setAge(age.getYears());
		mapper.save(user);

		return user;
	}

}
