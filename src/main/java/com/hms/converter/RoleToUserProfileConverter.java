package com.hms.converter;

import com.hms.model.UserProfile;
import com.hms.service.UserProfileService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile> {

    static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

    @Autowired
    UserProfileService userProfileService;

    /**
     * Gets UserProfile by Id
     */
    @NotNull
    @Override
    public UserProfile convert(Object element) {
        UserProfile profile = userProfileService.findById(Integer.parseInt((String) element));
        logger.info("Profile : {}", profile);
        return profile;
    }

}