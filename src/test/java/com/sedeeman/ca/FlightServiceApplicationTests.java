package com.sedeeman.ca;

import com.sedeeman.ca.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FlightServiceApplicationTests {

	@Autowired
	private FlightService flightService;

	@Test
	void contextLoads() {
		assertNotNull(flightService);
	}

}
