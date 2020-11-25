package com.github.cjgmj.dynamicquery.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

/**
 * Dummy entity to test queries
 * 
 * @author cjgmj
 *
 */

@Table(name = "dummies")
@Entity(name = "dummy")
@Getter
public class DummyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String surname;
	private Boolean customer;
	private LocalDate birthday;

	@OneToOne
	private DummyAddressEntity address;
}