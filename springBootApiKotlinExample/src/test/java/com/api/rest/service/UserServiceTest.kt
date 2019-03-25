package com.api.rest.service

import com.api.rest.entities.User
import com.api.rest.exceptions.UserMissingInformationException
import com.api.rest.exceptions.UserNotFoundException
import com.api.rest.repository.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.mock.web.MockHttpServletRequest
import java.time.Instant
import java.util.*


@RunWith(MockitoJUnitRunner::class)
internal class UserServiceTest{


    @Mock
    private lateinit var userRepository: UserRepository

    @Captor
    private lateinit var userCaptor: ArgumentCaptor<User>

    @InjectMocks
    private lateinit var userService: UserService



    @Test
    fun `get all users` (){

        whenever(userRepository.findAll()).thenReturn(mutableListOf())

        userService.getAll()

        verify(userRepository).findAll()




    }


    @Test
    fun `get existing user` (){

        whenever(userRepository.findById(any())).thenReturn(Optional.of(User()))

        userService.findUserIfExists("")

        verify(userRepository).findById(any())


    }


    @Test(expected = UserNotFoundException::class)
    fun `get not existing user` (){

        whenever(userRepository.findById(any())).thenReturn(Optional.empty())

        userService.findUserIfExists("")

        verify(userRepository).findById(any())


    }





    @Test
    fun `Create user`(){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }
        val httpServletRequest = MockHttpServletRequest()

        whenever(userRepository.insert(any<User>())).thenReturn(user)
        assert(userService.add(user,httpServletRequest).body == user)
        verify(userRepository, times(1)).insert(any<User>())



    }


    @Test
    fun `Create user contains user location in the header response`(){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }
        val httpServletRequest = MockHttpServletRequest()

        whenever(userRepository.insert(any<User>())).thenReturn(user)

        val responseHeader = userService.add(user,httpServletRequest).headers["Location"]?.get(0)

        assert(responseHeader == "${httpServletRequest.requestURL}/${user.id}")


    }




    @Test(expected = UserMissingInformationException::class)
    fun `Create user without name`(){
        val user = User().apply {
            id = "asdjkasd123"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }

        val httpServletRequest = MockHttpServletRequest()

        whenever(userService.checkUser(user)).thenReturn(null)


        userService.add(user,httpServletRequest)

        verify(userService.checkUser(user))

    }

    @Test(expected = UserMissingInformationException::class)
    fun `Create user without date`(){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"

        }
        val httpServletRequest = MockHttpServletRequest()

        whenever(userService.checkUser(user)).thenReturn(null)

        userService.add(user,httpServletRequest)


        verify(userService.checkUser(user))

    }


    @Test
    fun `modify name User with PUT method` (){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }

        val userUpdates = User().apply {
            id = "asdjkasd123"
            name="Juan"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }


        whenever(userRepository.findById(any())).thenReturn(Optional.of(user))
        whenever(userRepository.save(any<User>())).thenReturn(user)

        userService.putUpdate("",userUpdates)

        verify(userRepository).findById(any())
        verify(userRepository).save(userCaptor.capture())

        assert(userCaptor.value.name == userUpdates.name)


    }

    @Test
    fun `modify date User with PUT method` (){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }

        val userUpdates = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1991-12-03T10:15:30Z"))
        }


        whenever(userRepository.findById(any())).thenReturn(Optional.of(user))
        whenever(userRepository.save(any<User>())).thenReturn(user)

        userService.putUpdate("",userUpdates)

        verify(userRepository).findById(any())
        verify(userRepository).save(userCaptor.capture())

        assert(userCaptor.value.birthdate == userUpdates.birthdate)


    }


    @Test
    fun `modify All User fields with PUT method` (){

        val user = User().apply {
            id = "asdjkasd123"
            name="Alejandro"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }

        val userUpdates = User().apply {
            id = "asdjkasd123"
            name="Juan"
            birthdate = Date.from(Instant.parse("1990-12-03T10:15:30Z"))
        }


        whenever(userRepository.findById(any())).thenReturn(Optional.of(user))
        whenever(userRepository.save(any<User>())).thenReturn(user)

        userService.putUpdate("",userUpdates)

        verify(userRepository).findById(any())
        verify(userRepository).save(userCaptor.capture())

        assert(userCaptor.value.birthdate == userUpdates.birthdate)
        assert(userCaptor.value.name == userUpdates.name)


    }

   @Test
   fun `delete user` (){

        whenever(userRepository.findById(any())).thenReturn(Optional.of(User()))


       userService.delete("")

       verify(userRepository).findById(any())
       verify(userRepository).deleteById(any())

   }




}