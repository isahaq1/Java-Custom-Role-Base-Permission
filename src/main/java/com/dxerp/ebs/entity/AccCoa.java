package com.dxerp.ebs.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "ACC_COA")
public class AccCoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COA_NAME", nullable = false)
    private String coaName;

    @Column(name = "COA_TYPE", nullable = false)
    private String coaType;

    @Column(name = "IS_GROUP_HEAD", nullable = false)
    private Integer isGroupHead;

    @Column(name = "PARENT_ACC_COA_ID")
    private Long parentAccCoaId;

    @Column(name = "SORT_BY", nullable = false)
    private Integer sortBy;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "GROUP_CODE")
    private String groupCode;

    @Column(name = "CODE")
    private String code;

    @Column(name = "COMPANY_CODE")
    private String companyCode;

    @Column(name = "IS_SPECIAL_GL")
    private Integer isSpecialGl;

    @Column(name = "GC_BK")
    private String gcBk;

    // Getters and setters
}
