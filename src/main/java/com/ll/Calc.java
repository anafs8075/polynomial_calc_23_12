package com.ll;

public class Calc {
    public static int run(String exp) {
      exp = exp.trim(); // 여백 지움
      exp = stripOuterBracket(exp); // 괄호 제거

      // 연산기호가 없으면 바로 리턴
      if (!exp.contains(" ")) return Integer.parseInt(exp); // " "을 포함한다면 리턴
      boolean needToMultiply = exp.contains(" * "); // needToMultiplay에 " * " 을 포함
      boolean needToPlus = exp.contains(" + ") || exp.contains(" - "); // needToPlus에 " + " 나 " - " 을 포함
      boolean needToCompound = needToMultiply && needToPlus; //needTocompound는 multiplay랑 toplus가 둘다 참일때
      boolean needToSplit = exp.contains("(") || exp.contains(")"); // ToSplit에  "(" or ")" 을 포함시킨다

        if (needToSplit) {  // 괄호가 포함 되어 있다면

          int splitPointIndex = findSplitPointIndex(exp); // 정수 splitPointIndex에 findSplitPointIndex(exp)을 넣는다

          String firstExp = exp.substring(0, splitPointIndex); // 문자열 firstExp에 exp.substring(0, splitPointIndex) 을 넣는다.
          String secondExp = exp.substring(splitPointIndex + 1); // 문자열 secondExp에 exp.substring(splitPointIndex + 1)을 넣는다.

          char operator = exp.charAt(splitPointIndex); // operator에 exp.charAt(splitPointIndex) 을 넣는다

          exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);
          // exp는 firstExp + 여백 + operator + 여백 + secondExp

          return Calc.run(exp); // 리턴 값 exp
        } else if (needToCompound) { // " * "을 포함하면서 " + " 나 " - " 을 포함한다면
          String[] bits = exp.split(" \\+ "); // bits에 exp.split("\\+ ")을 넣는다

          return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO // 리턴값 정수 bits[0] + bits[1]
        }
        if (needToPlus) { // " + " 나 " - " 을 포함한다면
          exp = exp.replaceAll("\\- ", "\\+ \\-"); // exp 는
          String[] bits = exp.split(" \\+ "); // bits 는 exp.split(" \\+ "
          int sum = 0;
          for (int i = 0; i < bits.length; i++) {
            sum += Integer.parseInt(bits[i]);
          }
          return sum;
        } else if (needToMultiply) {
          String[] bits = exp.split(" \\* ");
          int rs = 1;
          for (int i = 0; i < bits.length; i++) {
            rs *= Integer.parseInt(bits[i]);
          }
          return rs;
        }
        throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
      }

      private static int findSplitPointIndexBy(String exp, char findChar) {
        int bracketCount = 0;

        for (int i = 0; i < exp.length(); i++) {
          char c = exp.charAt(i);

          if (c == '(') {
            bracketCount++;
          } else if (c == ')') {
            bracketCount--;
          } else if (c == findChar) {
            if (bracketCount == 0) return i;
          }
        }
        return -1;
      }

      private static int findSplitPointIndex(String exp) {
        int index = findSplitPointIndexBy(exp, '+');

        if (index >= 0) return index;

        return findSplitPointIndexBy(exp, '*');
      }

      private static String stripOuterBracket(String exp) {
        int outerBracketCount = 0;

        while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
          outerBracketCount++;
        }
        if (outerBracketCount == 0) return exp;
        return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
      }
    }