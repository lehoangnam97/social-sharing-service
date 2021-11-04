package com.namle.socialsharing.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZaloMetadataDTO {
    private String url;
    private String title;
    private String image;
    private String description;
}
