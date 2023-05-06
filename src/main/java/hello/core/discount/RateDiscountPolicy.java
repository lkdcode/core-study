package hello.core.discount;

import hello.core.member.Member;

import static hello.core.member.Grade.VIP;

public class RateDiscountPolicy implements DiscountPolicy {
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == VIP) return price * discountPercent / 100;
        else return 0;
    }
}
