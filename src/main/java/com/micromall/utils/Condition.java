package com.micromall.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/09.
 */
public class Condition {

	private String         orderBy;
	private List<Criteria> criterias;

	public Condition() {
		this(null);
	}

	public Condition(String orderBy) {
		this.orderBy = orderBy;
		criterias = new ArrayList<Criteria>();
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<Criteria> getCriterias() {
		return criterias;
	}

	public Condition or(Criteria criteria) {
		criteria.setCondition(this);
		criterias.add(criteria);
		return this;
	}

	public Criteria or() {
		Criteria criteria = new Criteria(this);
		criterias.add(criteria);
		return criteria;
	}

	public void clear() {
		criterias.clear();
		orderBy = null;
	}

	protected abstract static class GeneratedCriteria {

		protected List<Criterion> criterions;
		private   Condition       condition;

		protected GeneratedCriteria() {
			this(null);
		}

		protected GeneratedCriteria(Condition condition) {
			criterions = new ArrayList<Criterion>();
			this.condition = condition;
		}

		public boolean isValid() {
			return criterions.size() > 0;
		}

		public List<Criterion> getCriterions() {
			return criterions;
		}

		protected Condition getCondition() {
			return condition;
		}

		protected void setCondition(Condition condition) {
			this.condition = condition;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criterions.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criterions.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criterions.add(new Criterion(condition, value1, value2));
		}

		/**
		 * #{field} is null
		 */
		public Criteria andIsNull(String field) {
			addCriterion(field + " is null");
			return (Criteria)this;
		}

		/**
		 * #{field} is not null
		 */
		public Criteria andIsNotNull(String field) {
			addCriterion(field + " is not null");
			return (Criteria)this;
		}

		/**
		 * #{field} = #{value}
		 */
		public Criteria andEqualTo(String field, Object value) {
			addCriterion(field + " =", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} <> #{value}
		 */
		public Criteria andNotEqualTo(String field, Object value) {
			addCriterion(field + " <>", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} > #{value}
		 */
		public Criteria andGreaterThan(String field, Object value) {
			addCriterion(field + " >", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} >= #{value}
		 */
		public Criteria andGreaterThanOrEqualTo(String field, Object value) {
			addCriterion(field + " >=", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} < #{value}
		 */
		public Criteria andLessThan(String field, Object value) {
			addCriterion(field + " <", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} <= #{value}
		 */
		public Criteria andLessThanOrEqualTo(String field, Object value) {
			addCriterion(field + " <=", value, field);
			return (Criteria)this;
		}

		/**
		 * #{field} in #{values}
		 */
		public Criteria andIn(String field, List<?> values) {
			addCriterion(field + " in", values, field);
			return (Criteria)this;
		}

		/**
		 * #{field} not in #{values}
		 */
		public Criteria andNotIn(String field, List<?> values) {
			addCriterion(field + " not in", values, field);
			return (Criteria)this;
		}

		/**
		 * #{field} between #{value1} and #{value2}
		 */
		public Criteria andBetween(String field, Object value1, Object value2) {
			addCriterion(field + " between", value1, value2, field);
			return (Criteria)this;
		}

		/**
		 * #{field} not between #{value1} and #{value2}
		 */
		public Criteria andIdNotBetween(String field, Object value1, Object value2) {
			addCriterion(field + " not between", value1, value2, field);
			return (Criteria)this;
		}

	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}

		public Criteria(Condition condition) {
			super(condition);
		}

		public static Criteria create() {
			return new Criteria();
		}

		public Condition build() {
			return this.build(null);
		}

		public Condition build(String orderBy) {
			Condition condition = this.getCondition();
			if (condition == null) {
				condition = new Condition();
				this.setCondition(condition.or(this));
			}
			if (orderBy != null) {
				condition.setOrderBy(orderBy);
			}
			return condition;
		}
	}

	public static class Criterion {

		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value) {
			super();
			this.condition = condition;
			this.value = value;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.betweenValue = true;
		}

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

	}

}
