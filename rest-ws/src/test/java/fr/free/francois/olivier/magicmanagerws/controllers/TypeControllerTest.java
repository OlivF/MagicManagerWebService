package fr.free.francois.olivier.magicmanagerws.controllers;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.free.francois.olivier.magicmanagerws.config.ContextConfig;
import fr.free.francois.olivier.magicmanagerws.config.WebMvcConfig;
import fr.free.francois.olivier.magicmanagerws.services.TypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ContextConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class TypeControllerTest {

	@Resource 
    private WebApplicationContext wac;
    
    @Resource
    private TypeService typeServiceMock;
    
    private MockMvc mockMvc;
    
    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void checkGetTypeByIdUrl() throws Exception{
    	mockMvc.perform(MockMvcRequestBuilders.get("/api/type/1"))
              .andExpect(MockMvcResultMatchers.status().isOk());

    }
    
    @Test
    public void checkGetTypesUrl() throws Exception{
       mockMvc.perform(MockMvcRequestBuilders.get("/api/types"))
              .andExpect(MockMvcResultMatchers.status().isOk());
    }
   
    @Test
    public void getCards() throws Exception{
       mockMvc.perform(MockMvcRequestBuilders.get("/api/cards/type/1"))
              .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
