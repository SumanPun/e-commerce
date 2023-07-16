package com.webApp.ecommerce.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webApp.ecommerce.Controller.CategoryController;
import com.webApp.ecommerce.Model.Category;
import com.webApp.ecommerce.Model.Product;
import com.webApp.ecommerce.Repositories.CategoryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class categoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    Category record = new Category(1,"shoes",new ArrayList<>());
    Category record1 = new Category(1,"shoes",new ArrayList<>());
    Category record2 = new Category(1,"shoes",new ArrayList<>());


}
