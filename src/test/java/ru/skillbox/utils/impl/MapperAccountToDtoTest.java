//package ru.skillbox.utils.impl;
//
//
//import org.junit.jupiter.api.Test;
//import ru.skillbox.dto.AccountDto;
//import ru.skillbox.entity.Account;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class MapperAccountToDtoTest {
//
//    @Test
//    void testConvertAccountToDto_WithValidAccount() {
//        // Arrange
//        Account account = new Account();
//        account.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
//        account.setFirstName("John");
//        account.setLastName("Doe");
//
//        // Act
//        AccountDto accountDto = MapperAccountToDto.convertAccountToDto(account);
//
//        // Assert
//        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), accountDto.getId());
//        assertEquals("John", accountDto.getFirstName());
//        assertEquals("Doe", accountDto.getLastName());
//    }
//
//
//    @Test
//    void testConvertAccountToDto_WithNullAccount() {
//        // Act
//        AccountDto accountDto = MapperAccountToDto.convertAccountToDto(null);
//
//        // Assert
//        assertNull(accountDto);
//    }
//}
