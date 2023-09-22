package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.dto.ResponseDto;
import com.sameh.quizapp.mappper.ResponseMapper;
import com.sameh.quizapp.model.Response;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapperImpl implements ResponseMapper {
    @Override
    public ResponseDto mapToDto(Response response) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setId(response.getId());
        responseDto.setResponse(response.getResponse());

        return responseDto;
    }

    @Override
    public Response mapToEntity(ResponseDto responseDto) {
        Response response = new Response();
        response.setId(responseDto.getId());
        response.setResponse(responseDto.getResponse());
        return response;
    }
}
