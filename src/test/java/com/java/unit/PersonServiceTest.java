package com.java.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.unit.models.Person;
import com.java.unit.repositories.PersonRepository;
import com.java.unit.services.PersonService;

@Extensions(value = {
    @ExtendWith(value = MockitoExtension.class)
})
public class PersonServiceTest {
    
    /**
     * kita bisa langsung membuat object mock nya dengan menggunakan annotasi @Mock
     * kita tidak perlu membuat manual seperti ini :
     * Mockito.mock(Interface.class);
     * karnya ketika kita menggunakan annotasi @Mock() secara otomatis object mock
     * akan di buatkan secara otomatis oleh Mockito nya dengan bantuan MockitoExtension
     * yang kita sudah registrasikan diatas.
     */
    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    // disni kita inject personService nya
    @BeforeEach()
    public void setUp() {
        this.personService = new PersonService(personRepository);
    }

    @Test
    public void testGetPersonNotFound() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.personService.getPersonById("1");
        });
    }

    @Test
    public void testGetPersonSuccess() {

        /**
         * menambahkan beavior kepada mock object (personRepository)
         * sekenarionya sebagai berikut :
         * ketika method findByPersonId("1") di panggill maka 
         * akan mereturn object new Person("1", "Alliano")
         */
        Mockito.when(this.personRepository.findByPersonId("1")).thenReturn(new Person("1", "Alliano"));

        Assertions.assertNotNull(this.personRepository.findByPersonId("1"));

        Assertions.assertEquals(this.personRepository.findByPersonId("1"), new Person("1", "Alliano"));

        // ini bacanya jikalau method findByPersonId("1") ini dipanggil lebih dari 2 kali
        // maka akan men throw exception dan unit test akan gagal.
        Mockito.verify(this.personRepository, Mockito.times(2)).findByPersonId("1");
    }
}
