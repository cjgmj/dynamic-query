package com.cjgmj.dynamicQuery.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
