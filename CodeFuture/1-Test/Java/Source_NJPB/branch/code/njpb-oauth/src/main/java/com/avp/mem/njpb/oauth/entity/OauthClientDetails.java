package com.avp.mem.njpb.oauth.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 该类仅用于处理使用hsql时的ClientDetails问题，不作任何其它实际用途。
 * 当使用hsql时，若无此实体类，会导致找不到oauth_client_details表的错误。推测起来，
 * 应该是与JPA的初始化有关：可能当JPA未发现该实体类时，便不去获取相关表的信息。
 * 当使用独立数据库时，此实体类不是必须的。
 * 另，Spring在处理时，可能按照内置的BaseClientDetails方式来处理该实体对象，因此会导致
 * JSON序列化方面的异常（EOFException）。由于并不影响认证过程，而内存数据库仅作功能开发用，
 * 故将不专门纠正。
 * @author Hongfei
 */
@Data
@Entity
@Table(name = "oauth_client_details")
public class OauthClientDetails{
    @Id
    @Basic(optional = false)
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "resource_ids")
    private String resourceIds;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "scope")
    private String scope;
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(name = "authorities")
    private String authorities;
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;
    @Column(name = "additional_information")
    private String additionalInformation;
    @Column(name = "autoapprove")
    private String autoapprove;
}
