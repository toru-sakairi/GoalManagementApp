/*
    Goalクラスをスーパークラスとするサブクラスの種類を判別するのに使用する（フレームワークを判別する）
 */

package com.example.ver2.dataClass.goalManagement;

public enum GoalType {
    SMART("SMART"),
    BENCHMARKING("ベンチマーキング"),
    WILL_CAN_MUST("WillCanMust"),
    MEMO_GOAL("メモ"),
    GOAL("goal");

    private final String value;

    GoalType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}