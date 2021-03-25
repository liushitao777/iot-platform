package com.cpiinfo.sysmgt.entity;

import java.util.List;

public class TestUser {
    private String id;

    private String userId;

    private String userName;

    private String deptId;

    private List<TestDept> testDepts;
    private List<TestDept2> testDepts2;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
	public String toString() {
		return "TestUser [id=" + id + ", userId=" + userId + ", userName=" + userName + ", deptId=" + deptId + "]";
	}

	public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

	public List<TestDept> getTestDepts() {
		return testDepts;
	}

	public void setTestDepts(List<TestDept> testDepts) {
		this.testDepts = testDepts;
	}

	public List<TestDept2> getTestDepts2() {
		return testDepts2;
	}

	public void setTestDepts2(List<TestDept2> testDepts2) {
		this.testDepts2 = testDepts2;
	}

	
}