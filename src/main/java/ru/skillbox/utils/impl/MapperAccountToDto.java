//package ru.skillbox.utils.impl;
//
//import lombok.experimental.UtilityClass;
//import ru.skillbox.dto.AccountDto;
//import ru.skillbox.entity.Account;
//
//@UtilityClass
//public class MapperAccountToDto {
//
//    public static AccountDto convertAccountToDto(Account account) {
//        if (account == null) {
//            return null;
//        }
//        return AccountDto.builder()
//                .id(account.getId())
//                .firstName(account.getFirstName())
//                .lastName(account.getLastName())
//                .build();
//    }
//}
