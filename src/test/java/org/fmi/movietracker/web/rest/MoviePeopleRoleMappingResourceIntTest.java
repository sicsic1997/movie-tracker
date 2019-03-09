package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.MoviePeopleRoleMapping;
import org.fmi.movietracker.repository.MoviePeopleRoleMappingRepository;
import org.fmi.movietracker.service.MoviePeopleRoleMappingService;
import org.fmi.movietracker.service.dto.MoviePeopleRoleMappingDTO;
import org.fmi.movietracker.service.mapper.MoviePeopleRoleMappingMapper;
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
 * Test class for the MoviePeopleRoleMappingResource REST controller.
 *
 * @see MoviePeopleRoleMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class MoviePeopleRoleMappingResourceIntTest {

    @Autowired
    private MoviePeopleRoleMappingRepository moviePeopleRoleMappingRepository;

    @Autowired
    private MoviePeopleRoleMappingMapper moviePeopleRoleMappingMapper;

    @Autowired
    private MoviePeopleRoleMappingService moviePeopleRoleMappingService;

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

    private MockMvc restMoviePeopleRoleMappingMockMvc;

    private MoviePeopleRoleMapping moviePeopleRoleMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoviePeopleRoleMappingResource moviePeopleRoleMappingResource = new MoviePeopleRoleMappingResource(moviePeopleRoleMappingService);
        this.restMoviePeopleRoleMappingMockMvc = MockMvcBuilders.standaloneSetup(moviePeopleRoleMappingResource)
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
    public static MoviePeopleRoleMapping createEntity(EntityManager em) {
        MoviePeopleRoleMapping moviePeopleRoleMapping = new MoviePeopleRoleMapping();
        return moviePeopleRoleMapping;
    }

    @Before
    public void initTest() {
        moviePeopleRoleMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoviePeopleRoleMapping() throws Exception {
        int databaseSizeBeforeCreate = moviePeopleRoleMappingRepository.findAll().size();

        // Create the MoviePeopleRoleMapping
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO = moviePeopleRoleMappingMapper.toDto(moviePeopleRoleMapping);
        restMoviePeopleRoleMappingMockMvc.perform(post("/api/movie-people-role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePeopleRoleMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the MoviePeopleRoleMapping in the database
        List<MoviePeopleRoleMapping> moviePeopleRoleMappingList = moviePeopleRoleMappingRepository.findAll();
        assertThat(moviePeopleRoleMappingList).hasSize(databaseSizeBeforeCreate + 1);
        MoviePeopleRoleMapping testMoviePeopleRoleMapping = moviePeopleRoleMappingList.get(moviePeopleRoleMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createMoviePeopleRoleMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moviePeopleRoleMappingRepository.findAll().size();

        // Create the MoviePeopleRoleMapping with an existing ID
        moviePeopleRoleMapping.setId(1L);
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO = moviePeopleRoleMappingMapper.toDto(moviePeopleRoleMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoviePeopleRoleMappingMockMvc.perform(post("/api/movie-people-role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePeopleRoleMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MoviePeopleRoleMapping in the database
        List<MoviePeopleRoleMapping> moviePeopleRoleMappingList = moviePeopleRoleMappingRepository.findAll();
        assertThat(moviePeopleRoleMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMoviePeopleRoleMappings() throws Exception {
        // Initialize the database
        moviePeopleRoleMappingRepository.saveAndFlush(moviePeopleRoleMapping);

        // Get all the moviePeopleRoleMappingList
        restMoviePeopleRoleMappingMockMvc.perform(get("/api/movie-people-role-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moviePeopleRoleMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMoviePeopleRoleMapping() throws Exception {
        // Initialize the database
        moviePeopleRoleMappingRepository.saveAndFlush(moviePeopleRoleMapping);

        // Get the moviePeopleRoleMapping
        restMoviePeopleRoleMappingMockMvc.perform(get("/api/movie-people-role-mappings/{id}", moviePeopleRoleMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moviePeopleRoleMapping.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMoviePeopleRoleMapping() throws Exception {
        // Get the moviePeopleRoleMapping
        restMoviePeopleRoleMappingMockMvc.perform(get("/api/movie-people-role-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoviePeopleRoleMapping() throws Exception {
        // Initialize the database
        moviePeopleRoleMappingRepository.saveAndFlush(moviePeopleRoleMapping);

        int databaseSizeBeforeUpdate = moviePeopleRoleMappingRepository.findAll().size();

        // Update the moviePeopleRoleMapping
        MoviePeopleRoleMapping updatedMoviePeopleRoleMapping = moviePeopleRoleMappingRepository.findById(moviePeopleRoleMapping.getId()).get();
        // Disconnect from session so that the updates on updatedMoviePeopleRoleMapping are not directly saved in db
        em.detach(updatedMoviePeopleRoleMapping);
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO = moviePeopleRoleMappingMapper.toDto(updatedMoviePeopleRoleMapping);

        restMoviePeopleRoleMappingMockMvc.perform(put("/api/movie-people-role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePeopleRoleMappingDTO)))
            .andExpect(status().isOk());

        // Validate the MoviePeopleRoleMapping in the database
        List<MoviePeopleRoleMapping> moviePeopleRoleMappingList = moviePeopleRoleMappingRepository.findAll();
        assertThat(moviePeopleRoleMappingList).hasSize(databaseSizeBeforeUpdate);
        MoviePeopleRoleMapping testMoviePeopleRoleMapping = moviePeopleRoleMappingList.get(moviePeopleRoleMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMoviePeopleRoleMapping() throws Exception {
        int databaseSizeBeforeUpdate = moviePeopleRoleMappingRepository.findAll().size();

        // Create the MoviePeopleRoleMapping
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO = moviePeopleRoleMappingMapper.toDto(moviePeopleRoleMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoviePeopleRoleMappingMockMvc.perform(put("/api/movie-people-role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePeopleRoleMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MoviePeopleRoleMapping in the database
        List<MoviePeopleRoleMapping> moviePeopleRoleMappingList = moviePeopleRoleMappingRepository.findAll();
        assertThat(moviePeopleRoleMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoviePeopleRoleMapping() throws Exception {
        // Initialize the database
        moviePeopleRoleMappingRepository.saveAndFlush(moviePeopleRoleMapping);

        int databaseSizeBeforeDelete = moviePeopleRoleMappingRepository.findAll().size();

        // Delete the moviePeopleRoleMapping
        restMoviePeopleRoleMappingMockMvc.perform(delete("/api/movie-people-role-mappings/{id}", moviePeopleRoleMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoviePeopleRoleMapping> moviePeopleRoleMappingList = moviePeopleRoleMappingRepository.findAll();
        assertThat(moviePeopleRoleMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoviePeopleRoleMapping.class);
        MoviePeopleRoleMapping moviePeopleRoleMapping1 = new MoviePeopleRoleMapping();
        moviePeopleRoleMapping1.setId(1L);
        MoviePeopleRoleMapping moviePeopleRoleMapping2 = new MoviePeopleRoleMapping();
        moviePeopleRoleMapping2.setId(moviePeopleRoleMapping1.getId());
        assertThat(moviePeopleRoleMapping1).isEqualTo(moviePeopleRoleMapping2);
        moviePeopleRoleMapping2.setId(2L);
        assertThat(moviePeopleRoleMapping1).isNotEqualTo(moviePeopleRoleMapping2);
        moviePeopleRoleMapping1.setId(null);
        assertThat(moviePeopleRoleMapping1).isNotEqualTo(moviePeopleRoleMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoviePeopleRoleMappingDTO.class);
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO1 = new MoviePeopleRoleMappingDTO();
        moviePeopleRoleMappingDTO1.setId(1L);
        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO2 = new MoviePeopleRoleMappingDTO();
        assertThat(moviePeopleRoleMappingDTO1).isNotEqualTo(moviePeopleRoleMappingDTO2);
        moviePeopleRoleMappingDTO2.setId(moviePeopleRoleMappingDTO1.getId());
        assertThat(moviePeopleRoleMappingDTO1).isEqualTo(moviePeopleRoleMappingDTO2);
        moviePeopleRoleMappingDTO2.setId(2L);
        assertThat(moviePeopleRoleMappingDTO1).isNotEqualTo(moviePeopleRoleMappingDTO2);
        moviePeopleRoleMappingDTO1.setId(null);
        assertThat(moviePeopleRoleMappingDTO1).isNotEqualTo(moviePeopleRoleMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moviePeopleRoleMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moviePeopleRoleMappingMapper.fromId(null)).isNull();
    }
}
