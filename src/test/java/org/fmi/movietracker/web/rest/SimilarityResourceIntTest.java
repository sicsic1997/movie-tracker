package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.Similarity;
import org.fmi.movietracker.repository.SimilarityRepository;
import org.fmi.movietracker.service.SimilarityService;
import org.fmi.movietracker.service.dto.SimilarityDTO;
import org.fmi.movietracker.service.mapper.SimilarityMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static org.fmi.movietracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SimilarityResource REST controller.
 *
 * @see SimilarityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class SimilarityResourceIntTest {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Autowired
    private SimilarityRepository similarityRepository;

    @Autowired
    private SimilarityMapper similarityMapper;

    @Autowired
    private SimilarityService similarityService;

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

    private MockMvc restSimilarityMockMvc;

    private Similarity similarity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SimilarityResource similarityResource = new SimilarityResource(similarityService);
        this.restSimilarityMockMvc = MockMvcBuilders.standaloneSetup(similarityResource)
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
    public static Similarity createEntity(EntityManager em) {
        Similarity similarity = new Similarity()
            .value(DEFAULT_VALUE);
        return similarity;
    }

    @Before
    public void initTest() {
        similarity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSimilarity() throws Exception {
        int databaseSizeBeforeCreate = similarityRepository.findAll().size();

        // Create the Similarity
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);
        restSimilarityMockMvc.perform(post("/api/similarities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isCreated());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeCreate + 1);
        Similarity testSimilarity = similarityList.get(similarityList.size() - 1);
        assertThat(testSimilarity.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSimilarityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = similarityRepository.findAll().size();

        // Create the Similarity with an existing ID
        similarity.setId(1L);
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSimilarityMockMvc.perform(post("/api/similarities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSimilarities() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        // Get all the similarityList
        restSimilarityMockMvc.perform(get("/api/similarities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(similarity.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        // Get the similarity
        restSimilarityMockMvc.perform(get("/api/similarities/{id}", similarity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(similarity.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSimilarity() throws Exception {
        // Get the similarity
        restSimilarityMockMvc.perform(get("/api/similarities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        int databaseSizeBeforeUpdate = similarityRepository.findAll().size();

        // Update the similarity
        Similarity updatedSimilarity = similarityRepository.findById(similarity.getId()).get();
        // Disconnect from session so that the updates on updatedSimilarity are not directly saved in db
        em.detach(updatedSimilarity);
        updatedSimilarity
            .value(UPDATED_VALUE);
        SimilarityDTO similarityDTO = similarityMapper.toDto(updatedSimilarity);

        restSimilarityMockMvc.perform(put("/api/similarities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isOk());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeUpdate);
        Similarity testSimilarity = similarityList.get(similarityList.size() - 1);
        assertThat(testSimilarity.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSimilarity() throws Exception {
        int databaseSizeBeforeUpdate = similarityRepository.findAll().size();

        // Create the Similarity
        SimilarityDTO similarityDTO = similarityMapper.toDto(similarity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSimilarityMockMvc.perform(put("/api/similarities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(similarityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Similarity in the database
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSimilarity() throws Exception {
        // Initialize the database
        similarityRepository.saveAndFlush(similarity);

        int databaseSizeBeforeDelete = similarityRepository.findAll().size();

        // Delete the similarity
        restSimilarityMockMvc.perform(delete("/api/similarities/{id}", similarity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Similarity> similarityList = similarityRepository.findAll();
        assertThat(similarityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Similarity.class);
        Similarity similarity1 = new Similarity();
        similarity1.setId(1L);
        Similarity similarity2 = new Similarity();
        similarity2.setId(similarity1.getId());
        assertThat(similarity1).isEqualTo(similarity2);
        similarity2.setId(2L);
        assertThat(similarity1).isNotEqualTo(similarity2);
        similarity1.setId(null);
        assertThat(similarity1).isNotEqualTo(similarity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SimilarityDTO.class);
        SimilarityDTO similarityDTO1 = new SimilarityDTO();
        similarityDTO1.setId(1L);
        SimilarityDTO similarityDTO2 = new SimilarityDTO();
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
        similarityDTO2.setId(similarityDTO1.getId());
        assertThat(similarityDTO1).isEqualTo(similarityDTO2);
        similarityDTO2.setId(2L);
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
        similarityDTO1.setId(null);
        assertThat(similarityDTO1).isNotEqualTo(similarityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(similarityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(similarityMapper.fromId(null)).isNull();
    }
}
