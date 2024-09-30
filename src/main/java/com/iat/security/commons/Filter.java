package com.iat.security.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data
public class Filter {
    private String field;
    private String value;
    private String typeComparation;

}
