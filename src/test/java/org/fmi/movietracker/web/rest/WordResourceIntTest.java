package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.Word;
import org.fmi.movietracker.repository.WordRepository;
import org.fmi.movietracker.service.WordService;
import org.fmi.movietracker.service.dto.WordDTO;
import org.fmi.movietracker.service.mapper.WordMapper;
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
 * Test class for the WordResource REST controller.
 *
 * @see WordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class WordResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCY = 1;
    private static final Integer UPDATED_FREQUENCY = 2;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordService wordService;

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

    private MockMvc restWordMockMvc;

    private Word word;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WordResource wordResource = new WordResource(wordService);
        this.restWordMockMvc = MockMvcBuilders.standaloneSetup(wordResource)
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
    public static Word createEntity(EntityManager em) {
        Word word = new Word()
            .value(DEFAULT_VALUE)
            .frequency(DEFAULT_FREQUENCY);
        return word;
    }

    @Before
    public void initTest() {
        word = createEntity(em);
    }

    @Test
    @Transactional
    public void createWord() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);
        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isCreated());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate + 1);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWord.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
    }

    @Test
    @Transactional
    public void createWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word with an existing ID
        word.setId(1L);
        WordDTO wordDTO = wordMapper.toDto(word);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = wordRepository.findAll().size();
        // set the field null
        word.setValue(null);

        // Create the Word, which fails.
        WordDTO wordDTO = wordMapper.toDto(word);

        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = wordRepository.findAll().size();
        // set the field null
        word.setFrequency(null);

        // Create the Word, which fails.
        WordDTO wordDTO = wordMapper.toDto(word);

        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWords() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList
        restWordMockMvc.perform(get("/api/words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(word.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)));
    }
    
    @Test
    @Transactional
    public void getWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", word.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(word.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY));
    }

    @Test
    @Transactional
    public void getNonExistingWord() throws Exception {
        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Update the word
        Word updatedWord = wordRepository.findById(word.getId()).get();
        // Disconnect from session so that the updates on updatedWord are not directly saved in db
        em.detach(updatedWord);
        updatedWord
            .value(UPDATED_VALUE)
            .frequency(UPDATED_FREQUENCY);
        WordDTO wordDTO = wordMapper.toDto(updatedWord);

        restWordMockMvc.perform(put("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isOk());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWord.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingWord() throws Exception {
        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordMockMvc.perform(put("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeDelete = wordRepository.findAll().size();

        // Delete the word
        restWordMockMvc.perform(delete("/api/words/{id}", word.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Word.class);
        Word word1 = new Word();
        word1.setId(1L);
        Word word2 = new Word();
        word2.setId(word1.getId());
        assertThat(word1).isEqualTo(word2);
        word2.setId(2L);
        assertThat(word1).isNotEqualTo(word2);
        word1.setId(null);
        assertThat(word1).isNotEqualTo(word2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordDTO.class);
        WordDTO wordDTO1 = new WordDTO();
        wordDTO1.setId(1L);
        WordDTO wordDTO2 = new WordDTO();
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO2.setId(wordDTO1.getId());
        assertThat(wordDTO1).isEqualTo(wordDTO2);
        wordDTO2.setId(2L);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO1.setId(null);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wordMapper.fromId(null)).isNull();
    }
}
