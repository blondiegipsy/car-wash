package com.utitech.carwash.model;

import java.time.ZonedDateTime;

public record Message(Integer washerId, String status, ZonedDateTime timer) {
}
