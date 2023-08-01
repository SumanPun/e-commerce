package com.webApp.ecommerce.Services;

import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.PromoCode;
import com.webApp.ecommerce.Payloads.PromoCodeDto;
import com.webApp.ecommerce.Repositories.PromoCodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Service
public class PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final ModelMapper modelMapper;

    public PromoCodeService(PromoCodeRepository promoCodeRepository, ModelMapper modelMapper) {
        this.promoCodeRepository = promoCodeRepository;
        this.modelMapper = modelMapper;
    }

    private String generateRandomCode() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();
        String generateCode = random.ints(leftLimit,rightLimit+1)
                .limit(targetStringLength)
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
                .toString();
        return generateCode;
    }

    public PromoCodeDto createPromoCode(PromoCodeDto promoCode) {
        LocalDateTime d = LocalDateTime.now();
        d.plusDays(2);
        System.out.println(d);
        PromoCode promoCode1 = this.modelMapper.map(promoCode,PromoCode.class);
        promoCode1.setCode(generateRandomCode());
        promoCode1.setExpirationDATE(d);
        this.promoCodeRepository.save(promoCode1);
        return this.modelMapper.map(promoCode1,PromoCodeDto.class);
    }

    public boolean promoCodeValid(String code) {
        boolean valid=false;
        PromoCode promoCode = this.promoCodeRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("code","codeName: "+ code,0));
        LocalDateTime d = LocalDateTime.now();
        int compareDates = promoCode.getExpirationDATE().compareTo(d);

        if(compareDates > 0 || compareDates == 0) {
            ///promo not expired
            valid = true;
        }else if(compareDates < 0) {
            //promo code expired
            valid = false;
        }
        return valid;
    }
}
