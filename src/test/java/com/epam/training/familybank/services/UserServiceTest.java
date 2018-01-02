package com.epam.training.familybank.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.epam.training.familybank.dao.jpaimpl.JpaUserDao;
import com.epam.training.familybank.domain.User;

public class UserServiceTest {
    private JpaUserDao mockJpaUserDao;
    private UserService underTest;
    
    @Before
    public void setUp() {
        mockJpaUserDao = mock(JpaUserDao.class);
        underTest = new UserService(mockJpaUserDao);
    }
    
    @Test
    public void testMockCreation(){
        assertNotNull(mockJpaUserDao);
    }
    
    @Test
    public void testGetUsers() {
        // Given
        List<User> expected = new ArrayList<>();
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        expected.add(user1);
        expected.add(user2);
        when(mockJpaUserDao.queryAllUsers()).thenReturn(expected);
        
        // When
        List<User> actual = underTest.getUsers();
        
        // Then
        assertEquals(expected, actual);
    }
}
