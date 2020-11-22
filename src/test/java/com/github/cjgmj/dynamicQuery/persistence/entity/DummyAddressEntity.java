package com.github.cjgmj.dynamicquery.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Table(name = "dummy_addresses")
@Entity(name = "address")
@Getter
public class DummyAddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String street;
	private Integer number;

	@OneToOne
	private DummyCityEntity city;
}