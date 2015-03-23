package com.syzton.sunread.service.user;

import com.syzton.sunread.model.security.VerificationToken;

public interface VerificationTokenService {

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);
    
    //TODO
    //public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken generateEmailVerificationToken(String emailAddress);
    
    //TODO
    //public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
