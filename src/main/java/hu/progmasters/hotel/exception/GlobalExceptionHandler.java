package hu.progmasters.hotel.exception;/*
 * Copyright © Progmasters (QTC Kft.), 2018.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed,
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft.
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

import com.fasterxml.jackson.core.JsonParseException;
import hu.progmasters.hotel.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("A validation error occurred: ", ex);
        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError validationError = new ValidationError();

        for (FieldError fieldError : fieldErrors) {
            validationError.addFieldError(fieldError.getField(), messageSource.getMessage(fieldError, Locale.getDefault()));
        }

        return validationError;
    }

    @ExceptionHandler(HotelHasNoRoomsException.class)
    public ResponseEntity<ApiError> handleHotelHasNoRoomsException(HotelHasNoRoomsException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("NO_ROOMS", "This Hotel has no available rooms", exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ApiError> handleHotelNotFoundException(HotelNotFoundException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("HOTEL_NOT_FOUND", "Hotel was not found in our database", exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ApiError> handleRoomNotFoundException(RoomNotFoundException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("ROOM_NOT_FOUND", "This room does not exist", exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomAlreadyDeletedException.class)
    public ResponseEntity<ApiError> handleRoomAlreadyDeletedException(RoomAlreadyDeletedException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("ROOM_ALREADY_DELETED", "This room is already deleted", exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApiError> handleJsonParseException(JsonParseException ex) {
        logger.error("Request JSON could no be parsed: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("JSON_PARSE_ERROR", "The request could not be parsed as a valid JSON.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal argument error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("ILLEGAL_ARGUMENT_ERROR", "An illegal argument has been passed to the method.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> defaultErrorHandler(Throwable t) {
        logger.error("An unexpected error occurred: ", t);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError body = new ApiError("UNCLASSIFIED_ERROR", "Oh, snap! Something really unexpected occurred.", t.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiError> handleReservationNotFound(ReservationNotFoundException exception) {
        logger.error("ReservationNotFound: ", exception);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("Reservation_Not_Found", "Can't delete", exception.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(ReservationAlreadyDeletedException.class)
    public ResponseEntity<ApiError> handleReservationAlreadyDeleted(ReservationAlreadyDeletedException exception) {
        logger.error("ReservationAlreadyDeleted: ", exception);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("Reservation_ALREADY_DELETED", "This reservation is no longer available as it was deleted.", exception.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ApiError> emailAlreadyInUse(EmailAlreadyInUseException emailAlreadyInUseException) {
        logger.error("EmailAlreadyInUse ", emailAlreadyInUseException);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("Email Already In Use", "Can't registration", emailAlreadyInUseException.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(HotelAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleHotelAlreadyExistsException(HotelAlreadyExistsException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("HOTEL_ALREADY_CREATED", "A hotel already exists with this name", exception.getLocalizedMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleRoomAlreadyExistsException(RoomAlreadyExistsException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("ROOM_NAME_ALREADY_TAKEN", "A room already exists with this name", exception.getLocalizedMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleReservationAlreadyExistsException(ReservationAlreadyExistsException exception){
        logger.error(exception.getMessage());
        ApiError result = new ApiError("RESERVATION_ALREADY_CREATED", "This reservation already exists", exception.getLocalizedMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> tokenException(TokenException exception) {
        logger.error(exception.getMessage());
        ApiError result = new ApiError("Toekn error", exception.getMessage(), exception.getLocalizedMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

