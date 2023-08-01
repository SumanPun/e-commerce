package com.webApp.ecommerce.Controller;

import com.webApp.ecommerce.Payloads.PromoCodeDto;
import com.webApp.ecommerce.Services.PromoCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promocode")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PromoCodeDto> createPromoCode(@RequestBody PromoCodeDto promoCodeDto) {
        PromoCodeDto promoCodeDto1 = this.promoCodeService.createPromoCode(promoCodeDto);
        return new ResponseEntity<>(promoCodeDto1, HttpStatus.CREATED);
    }

    @GetMapping("/checkValid")
    public ResponseEntity<Boolean> checkPromoCode(@RequestParam String code) {
        Boolean isValid = this.promoCodeService.promoCodeValid(code);
        return new ResponseEntity<>(isValid,HttpStatus.OK);
    }
}
