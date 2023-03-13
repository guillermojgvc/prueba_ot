package com.ontop.wallet.feign.error;

import feign.Response;

public interface FeignHttpExceptionHandler {
    Exception handle(Response response);
}