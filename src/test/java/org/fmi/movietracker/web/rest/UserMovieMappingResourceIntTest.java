package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.UserMovieMapping;
import org.fmi.movietracker.repository.UserMovieMappingRepository;
import org.fmi.movietracker.service.UserMovieMappingService;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;
import org.fmi.movietracker.service.mapper.UserMovieMappingMapper;
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
 * Test class for the UserMovieMappingResource REST controller.
 *
 * @see UserMovieMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class UserMovieMappingResourceIntTest {

    @Autowired
    private UserMovieMappingRepository userMovieMappingRepository;

    @Autowired
    private UserMovieMappingMapper userMovieMappingMapper;

    @Autowired
    private UserMovieMappingService userMovieMappingService;

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

    private MockMvc restUserMovieMappingMockMvc;

    private UserMovieMapping userMovieMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMovieMappingResource userMovieMappingResource = new UserMovieMappingResource(userMovieMappingService);
        this.restUserMovieMappingMockMvc = MockMvcBuilders.standaloneSetup(userMovieMappingResource)
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
    public static UserMovieMapping createEntity(EntityManager em) {
        UserMovieMapping userMovieMapping = new UserMovieMapping();
        return userMovieMapping;
    }

    @Before
    public void initTest() {
        userMovieMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMovieMapping() throws Exception {
        int databaseSizeBeforeCreate = userMovieMappingRepository.findAll().size();

        // Create the UserMovieMapping
        UserMovieMappingDTO userMovieMappingDTO = userMovieMappingMapper.toDto(userMovieMapping);
        restUserMovieMappingMockMvc.perform(post("/api/user-movie-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMovieMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMovieMapping in the database
        List<UserMovieMapping> userMovieMappingList = userMovieMappingRepository.findAll();
        assertThat(userMovieMappingList).hasSize(databaseSizeBeforeCreate + 1);
        UserMovieMapping testUserMovieMapping = userMovieMappingList.get(userMovieMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserMovieMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMovieMappingRepository.findAll().size();

        // Create the UserMovieMapping with an existing ID
        userMovieMapping.setId(1L);
        UserMovieMappingDTO userMovieMappingDTO = userMovieMappingMapper.toDto(userMovieMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMovieMappingMockMvc.perform(post("/api/user-movie-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMovieMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMovieMapping in the database
        List<UserMovieMapping> userMovieMappingList = userMovieMappingRepository.findAll();
        assertThat(userMovieMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserMovieMappings() throws Exception {
        // Initialize the database
        userMovieMappingRepository.saveAndFlush(userMovieMapping);

        // Get all the userMovieMappingList
        restUserMovieMappingMockMvc.perform(get("/api/user-movie-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMovieMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getUserMovieMapping() throws Exception {
        // Initialize the database
        userMovieMappingRepository.saveAndFlush(userMovieMapping);

        // Get the userMovieMapping
        restUserMovieMappingMockMvc.perform(get("/api/user-movie-mappings/{id}", userMovieMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMovieMapping.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserMovieMapping() throws Exception {
        // Get the userMovieMapping
        restUserMovieMappingMockMvc.perform(get("/api/user-movie-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMovieMapping() throws Exception {
        // Initialize the database
        userMovieMappingRepository.saveAndFlush(userMovieMapping);

        int databaseSizeBeforeUpdate = userMovieMappingRepository.findAll().size();

        // Update the userMovieMapping
        UserMovieMapping updatedUserMovieMapping = userMovieMappingRepository.findById(userMovieMapping.getId()).get();
        // Disconnect from session so that the updates on updatedUserMovieMapping are not directly saved in db
        em.detach(updatedUserMovieMapping);
        UserMovieMappingDTO userMovieMappingDTO = userMovieMappingMapper.toDto(updatedUserMovieMapping);

        restUserMovieMappingMockMvc.perform(put("/api/user-movie-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMovieMappingDTO)))
            .andExpect(status().isOk());

        // Validate the UserMovieMapping in the database
        List<UserMovieMapping> userMovieMappingList = userMovieMappingRepository.findAll();
        assertThat(userMovieMappingList).hasSize(databaseSizeBeforeUpdate);
        UserMovieMapping testUserMovieMapping = userMovieMappingList.get(userMovieMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMovieMapping() throws Exception {
        int databaseSizeBeforeUpdate = userMovieMappingRepository.findAll().size();

        // Create the UserMovieMapping
        UserMovieMappingDTO userMovieMappingDTO = userMovieMappingMapper.toDto(userMovieMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMovieMappingMockMvc.perform(put("/api/user-movie-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMovieMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMovieMapping in the database
        List<UserMovieMapping> userMovieMappingList = userMovieMappingRepository.findAll();
        assertThat(userMovieMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserMovieMapping() throws Exception {
        // Initialize the database
        userMovieMappingRepository.saveAndFlush(userMovieMapping);

        int databaseSizeBeforeDelete = userMovieMappingRepository.findAll().size();

        // Delete the userMovieMapping
        restUserMovieMappingMockMvc.perform(delete("/api/user-movie-mappings/{id}", userMovieMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserMovieMapping> userMovieMappingList = userMovieMappingRepository.findAll();
        assertThat(userMovieMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMovieMapping.class);
        UserMovieMapping userMovieMapping1 = new UserMovieMapping();
        userMovieMapping1.setId(1L);
        UserMovieMapping userMovieMapping2 = new UserMovieMapping();
        userMovieMapping2.setId(userMovieMapping1.getId());
        assertThat(userMovieMapping1).isEqualTo(userMovieMapping2);
        userMovieMapping2.setId(2L);
        assertThat(userMovieMapping1).isNotEqualTo(userMovieMapping2);
        userMovieMapping1.setId(null);
        assertThat(userMovieMapping1).isNotEqualTo(userMovieMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMovieMappingDTO.class);
        UserMovieMappingDTO userMovieMappingDTO1 = new UserMovieMappingDTO();
        userMovieMappingDTO1.setId(1L);
        UserMovieMappingDTO userMovieMappingDTO2 = new UserMovieMappingDTO();
        assertThat(userMovieMappingDTO1).isNotEqualTo(userMovieMappingDTO2);
        userMovieMappingDTO2.setId(userMovieMappingDTO1.getId());
        assertThat(userMovieMappingDTO1).isEqualTo(userMovieMappingDTO2);
        userMovieMappingDTO2.setId(2L);
        assertThat(userMovieMappingDTO1).isNotEqualTo(userMovieMappingDTO2);
        userMovieMappingDTO1.setId(null);
        assertThat(userMovieMappingDTO1).isNotEqualTo(userMovieMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userMovieMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userMovieMappingMapper.fromId(null)).isNull();
    }
}
