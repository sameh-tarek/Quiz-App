package com.sameh.quizapp.mappper;

import com.sameh.quizapp.dto.ResponseDto;
import com.sameh.quizapp.model.Response;

public interface ResponseMapper {

    ResponseDto mapToDto(Response response);

    Response mapToEntity(ResponseDto responseDto);
}