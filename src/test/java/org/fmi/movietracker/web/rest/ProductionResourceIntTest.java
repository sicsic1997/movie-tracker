package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.Production;
import org.fmi.movietracker.repository.ProductionRepository;
import org.fmi.movietracker.service.ProductionService;
import org.fmi.movietracker.service.dto.ProductionDTO;
import org.fmi.movietracker.service.mapper.ProductionMapper;
import org.fmi.movietracker.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static org.fmi.movietracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductionResource REST controller.
 *
 * @see ProductionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class ProductionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ProductionMapper productionMapper;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductionMockMvc;

    private Production production;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductionResource productionResource = new ProductionResource(productionService);
        this.restProductionMockMvc = MockMvcBuilders.standaloneSetup(productionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Production createEntity(EntityManager em) {
        Production production = new Production()
            .code(DEFAULT_CODE);
        return production;
    }

    @Before
    public void initTest() {
        production = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduction() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production
        ProductionDTO productionDTO = productionMapper.toDto(production);
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isCreated());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate + 1);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production with an existing ID
        production.setId(1L);
        ProductionDTO productionDTO = productionMapper.toDto(production);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setCode(null);

        // Create the Production, which fails.
        ProductionDTO productionDTO = productionMapper.toDto(production);

        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductions() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(production.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduction() throws Exception {
        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Update the production
        Production updatedProduction = productionRepository.findById(production.getId()).get();
        // Disconnect from session so that the updates on updatedProduction are not directly saved in db
        em.detach(updatedProduction);
        updatedProduction
            .code(UPDATED_CODE);
        ProductionDTO productionDTO = productionMapper.toDto(updatedProduction);

        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduction() throws Exception {
        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Create the Production
        ProductionDTO productionDTO = productionMapper.toDto(production);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeDelete = productionRepository.findAll().size();

        // Delete the production
        restProductionMockMvc.perform(delete("/api/productions/{id}", production.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Production.class);
        Production production1 = new Production();
        production1.setId(1L);
        Production production2 = new Production();
        production2.setId(production1.getId());
        assertThat(production1).isEqualTo(production2);
        production2.setId(2L);
        assertThat(production1).isNotEqualTo(production2);
        production1.setId(null);
        assertThat(production1).isNotEqualTo(production2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionDTO.class);
        ProductionDTO productionDTO1 = new ProductionDTO();
        productionDTO1.setId(1L);
        ProductionDTO productionDTO2 = new ProductionDTO();
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
        productionDTO2.setId(productionDTO1.getId());
        assertThat(productionDTO1).isEqualTo(productionDTO2);
        productionDTO2.setId(2L);
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
        productionDTO1.setId(null);
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productionMapper.fromId(null)).isNull();
    }
}
