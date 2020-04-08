package com.biat.sfgrestdocsexample.web.Controller;

import com.biat.sfgrestdocsexample.Repository.BeerRepository;
import com.biat.sfgrestdocsexample.domain.Beer;
import com.biat.sfgrestdocsexample.web.model.BeerDto;
import com.biat.sfgrestdocsexample.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.biat.sfgrestdocsexample.web.mappers")
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;
    @Test
    void getBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("iscold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/beer",
                        RequestDocumentation.pathParameters (
                                RequestDocumentation.parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("iscold").description("Is Beer Cold Query param")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("Id of Beer"),
                                PayloadDocumentation.fieldWithPath("version").description("Version number"),
                                PayloadDocumentation.fieldWithPath("createdDate").description("Date Created"),
                                PayloadDocumentation.fieldWithPath("lastModifiedDate").description("Date Updated"),
                                PayloadDocumentation.fieldWithPath("beerName").description("Beer Name"),
                                PayloadDocumentation.fieldWithPath("beerStyle").description("Beer Style"),
                                PayloadDocumentation.fieldWithPath("upc").description("UPC of Beer"),
                                PayloadDocumentation.fieldWithPath("price").description("Price"),
                                PayloadDocumentation.fieldWithPath("quantityOnHand").description("Quantity On hand")
                        )));

    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                 .andDo(MockMvcRestDocumentation.document("v1/beer",
                         PayloadDocumentation.requestFields(PayloadDocumentation.fieldWithPath("id").ignored(),
                                 PayloadDocumentation.fieldWithPath("version").ignored(),
                                 PayloadDocumentation.fieldWithPath("createdDate").ignored(),
                                 PayloadDocumentation.fieldWithPath("lastModifiedDate").ignored(),
                                 PayloadDocumentation.fieldWithPath("beerName").description("Beer Name"),
                                 PayloadDocumentation.fieldWithPath("beerStyle").description("Beer style"),
                                 PayloadDocumentation.fieldWithPath("upc").description("Beer UPC").attributes(),
                                 PayloadDocumentation.fieldWithPath("price").description("Beer price"),
                                 PayloadDocumentation.fieldWithPath("quantityOnHand").ignored())));
                                     }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("Nice Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();

    }
}