package solution10xx;

//给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
//
//
// 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
//
//
// 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
//
// 以这种方式修改数组后，返回数组 可能的最大和 。
//
//
//
// 示例 1：
//
//
//输入：nums = [4,2,3], k = 1
//输出：5
//解释：选择下标 1 ，nums 变为 [4,-2,3] 。
//
//
// 示例 2：
//
//
//输入：nums = [3,-1,0,2], k = 3
//输出：6
//解释：选择下标 (1, 2, 2) ，nums 变为 [3,1,0,2] 。
//
//
// 示例 3：
//
//
//输入：nums = [2,-3,-1,5,-4], k = 2
//输出：13
//解释：选择下标 (1, 4) ，nums 变为 [2,3,-1,5,4] 。
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 104
// -100 <= nums[i] <= 100
// 1 <= k <= 104
//
// Related Topics 贪心 数组 排序
// 👍 185 👎 0


import java.util.Comparator;
import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution1005 {
    public int largestSumAfterKNegations(int[] nums, int k) {
        //思路，贪心算法
        //简单看下用例的规律就是
        //1.如果有负数，先让负数变正
        //2.如果有0，那么可以在负数都变为正数的时，用0来清空剩余次数
        //3.如果有次数还有剩余，那么就得看次数是奇数还是偶数，偶数的无影响(负负得正)，奇数的话就用在最小的正数上
        //3.1 注意：这里重点是考虑到负数变为正数后，重新排列的情况
        //这里使用了两个优先级队列，优先级队列默认的排序就是由小到大
        //负数队列出队列的顺序就是先变为正数的顺序。变为正数时，就需要加入正数队列，进行排序
        //正数队列其实仅仅是用来取最小正数的，其实不用队列，用一个Math.min来获取最小正数也可以
        //还需要判断一下是否有0的存在
        PriorityQueue<Integer> belowZeroQueue = new PriorityQueue<>();
        PriorityQueue<Integer> aboveZeroQueue = new PriorityQueue<>();
        boolean hasZero = false;
        int result = 0;
        for (int num : nums) {
            result += num;
            if (num > 0) {
                aboveZeroQueue.add(num);
            } else if (num == 0) {
                hasZero = true;
            } else {
                belowZeroQueue.add(num);
            }
        }

        for (int i = 0; i < k; i++) {
            if (!belowZeroQueue.isEmpty()) {
                Integer currentNum = belowZeroQueue.poll();
                result -= 2 * currentNum;
                aboveZeroQueue.add(-currentNum);
            } else if (hasZero) {
                return result;
            } else {
                if (!aboveZeroQueue.isEmpty()) {
                    boolean isEven = (k - i) % 2 == 0;
                    return isEven ? result : result - 2 * aboveZeroQueue.poll();
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution1005 solution = new Solution1005();
        System.out.println(solution.largestSumAfterKNegations(new int[]{3,-1,0,2}, 3));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
