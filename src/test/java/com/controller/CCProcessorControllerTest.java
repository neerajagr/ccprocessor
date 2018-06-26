/**
 * 
 */
package com.controller;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.service.CCProcessorService;

/**
 * @author neeagarw
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CCProcessorControllerTest {

	@Autowired
	private CCProcessorService processorService;
	
	@InjectMocks
	private CCProcessorController ccProcessorController;
}
