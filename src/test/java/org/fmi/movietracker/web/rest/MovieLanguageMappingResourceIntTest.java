package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.MovieLanguageMapping;
import org.fmi.movietracker.repository.MovieLanguageMappingRepository;
import org.fmi.movietracker.service.MovieLanguageMappingService;
import org.fmi.movietracker.service.dto.MovieLanguageMappingDTO;
import org.fmi.movietracker.service.mapper.MovieLanguageMappingMapper;
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
 * Test class for the MovieLanguageMappingResource REST controller.
 *
 * @see MovieLanguageMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class MovieLanguageMappingResourceIntTest {

    @Autowired
    private MovieLanguageMappingRepository movieLanguageMappingRepository;

    @Autowired
    private MovieLanguageMappingMapper movieLanguageMappingMapper;

    @Autowired
    private MovieLanguageMappingService movieLanguageMappingService;

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

    private MockMvc restMovieLanguageMappingMockMvc;

    private MovieLanguageMapping movieLanguageMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieLanguageMappingResource movieLanguageMappingResource = new MovieLanguageMappingResource(movieLanguageMappingService);
        this.restMovieLanguageMappingMockMvc = MockMvcBuilders.standaloneSetup(movieLanguageMappingResource)
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
    public static MovieLanguageMapping createEntity(EntityManager em) {
        MovieLanguageMapping movieLanguageMapping = new MovieLanguageMapping();
        return movieLanguageMapping;
    }

    @Before
    public void initTest() {
        movieLanguageMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieLanguageMapping() throws Exception {
        int databaseSizeBeforeCreate = movieLanguageMappingRepository.findAll().size();

        // Create the MovieLanguageMapping
        MovieLanguageMappingDTO movieLanguageMappingDTO = movieLanguageMappingMapper.toDto(movieLanguageMapping);
        restMovieLanguageMappingMockMvc.perform(post("/api/movie-language-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieLanguageMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieLanguageMapping in the database
        List<MovieLanguageMapping> movieLanguageMappingList = movieLanguageMappingRepository.findAll();
        assertThat(movieLanguageMappingList).hasSize(databaseSizeBeforeCreate + 1);
        MovieLanguageMapping testMovieLanguageMapping = movieLanguageMappingList.get(movieLanguageMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createMovieLanguageMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieLanguageMappingRepository.findAll().size();

        // Create the MovieLanguageMapping with an existing ID
        movieLanguageMapping.setId(1L);
        MovieLanguageMappingDTO movieLanguageMappingDTO = movieLanguageMappingMapper.toDto(movieLanguageMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieLanguageMappingMockMvc.perform(post("/api/movie-language-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieLanguageMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieLanguageMapping in the database
        List<MovieLanguageMapping> movieLanguageMappingList = movieLanguageMappingRepository.findAll();
        assertThat(movieLanguageMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieLanguageMappings() throws Exception {
        // Initialize the database
        movieLanguageMappingRepository.saveAndFlush(movieLanguageMapping);

        // Get all the movieLanguageMappingList
        restMovieLanguageMappingMockMvc.perform(get("/api/movie-language-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieLanguageMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMovieLanguageMapping() throws Exception {
        // Initialize the database
        movieLanguageMappingRepository.saveAndFlush(movieLanguageMapping);

        // Get the movieLanguageMapping
        restMovieLanguageMappingMockMvc.perform(get("/api/movie-language-mappings/{id}", movieLanguageMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieLanguageMapping.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieLanguageMapping() throws Exception {
        // Get the movieLanguageMapping
        restMovieLanguageMappingMockMvc.perform(get("/api/movie-language-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieLanguageMapping() throws Exception {
        // Initialize the database
        movieLanguageMappingRepository.saveAndFlush(movieLanguageMapping);

        int databaseSizeBeforeUpdate = movieLanguageMappingRepository.findAll().size();

        // Update the movieLanguageMapping
        MovieLanguageMapping updatedMovieLanguageMapping = movieLanguageMappingRepository.findById(movieLanguageMapping.getId()).get();
        // Disconnect from session so that the updates on updatedMovieLanguageMapping are not directly saved in db
        em.detach(updatedMovieLanguageMapping);
        MovieLanguageMappingDTO movieLanguageMappingDTO = movieLanguageMappingMapper.toDto(updatedMovieLanguageMapping);

        restMovieLanguageMappingMockMvc.perform(put("/api/movie-language-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieLanguageMappingDTO)))
            .andExpect(status().isOk());

        // Validate the MovieLanguageMapping in the database
        List<MovieLanguageMapping> movieLanguageMappingList = movieLanguageMappingRepository.findAll();
        assertThat(movieLanguageMappingList).hasSize(databaseSizeBeforeUpdate);
        MovieLanguageMapping testMovieLanguageMapping = movieLanguageMappingList.get(movieLanguageMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieLanguageMapping() throws Exception {
        int databaseSizeBeforeUpdate = movieLanguageMappingRepository.findAll().size();

        // Create the MovieLanguageMapping
        MovieLanguageMappingDTO movieLanguageMappingDTO = movieLanguageMappingMapper.toDto(movieLanguageMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieLanguageMappingMockMvc.perform(put("/api/movie-language-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieLanguageMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieLanguageMapping in the database
        List<MovieLanguageMapping> movieLanguageMappingList = movieLanguageMappingRepository.findAll();
        assertThat(movieLanguageMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovieLanguageMapping() throws Exception {
        // Initialize the database
        movieLanguageMappingRepository.saveAndFlush(movieLanguageMapping);

        int databaseSizeBeforeDelete = movieLanguageMappingRepository.findAll().size();

        // Delete the movieLanguageMapping
        restMovieLanguageMappingMockMvc.perform(delete("/api/movie-language-mappings/{id}", movieLanguageMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieLanguageMapping> movieLanguageMappingList = movieLanguageMappingRepository.findAll();
        assertThat(movieLanguageMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieLanguageMapping.class);
        MovieLanguageMapping movieLanguageMapping1 = new MovieLanguageMapping();
        movieLanguageMapping1.setId(1L);
        MovieLanguageMapping movieLanguageMapping2 = new MovieLanguageMapping();
        movieLanguageMapping2.setId(movieLanguageMapping1.getId());
        assertThat(movieLanguageMapping1).isEqualTo(movieLanguageMapping2);
        movieLanguageMapping2.setId(2L);
        assertThat(movieLanguageMapping1).isNotEqualTo(movieLanguageMapping2);
        movieLanguageMapping1.setId(null);
        assertThat(movieLanguageMapping1).isNotEqualTo(movieLanguageMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieLanguageMappingDTO.class);
        MovieLanguageMappingDTO movieLanguageMappingDTO1 = new MovieLanguageMappingDTO();
        movieLanguageMappingDTO1.setId(1L);
        MovieLanguageMappingDTO movieLanguageMappingDTO2 = new MovieLanguageMappingDTO();
        assertThat(movieLanguageMappingDTO1).isNotEqualTo(movieLanguageMappingDTO2);
        movieLanguageMappingDTO2.setId(movieLanguageMappingDTO1.getId());
        assertThat(movieLanguageMappingDTO1).isEqualTo(movieLanguageMappingDTO2);
        movieLanguageMappingDTO2.setId(2L);
        assertThat(movieLanguageMappingDTO1).isNotEqualTo(movieLanguageMappingDTO2);
        movieLanguageMappingDTO1.setId(null);
        assertThat(movieLanguageMappingDTO1).isNotEqualTo(movieLanguageMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movieLanguageMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movieLanguageMappingMapper.fromId(null)).isNull();
    }
}
