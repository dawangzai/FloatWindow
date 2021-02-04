package com.wangzai.floatwindow

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//        val result = findRepeatNumber(intArrayOf(2, 3, 1, 2, 5, 3))
//        println("result = $result")

        val array = arrayOf(
            intArrayOf(1, 2, 3, 4, 5),
            intArrayOf(6, 7, 8, 9, 10),
            intArrayOf(11, 12, 13, 14, 15),
            intArrayOf(16, 17, 18, 19, 20),
            intArrayOf(21, 22, 23, 24, 25)
        )

        val result = findNumberIn2DArray(array,19)
        println("result = $result")
    }

    fun findRepeatNumber(nums: IntArray): Int {
        var temp: Int
        for (i in nums.indices) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    return nums[i]
                }
                temp = nums[nums[i]]
                nums[nums[i]] = nums[i]
                nums[i] = temp
            }
        }
        return -1
    }

    fun findNumberIn2DArray(matrix: Array<IntArray>, target: Int): Boolean {
        if (matrix.isEmpty() || matrix[0].isEmpty()) {
            return false
        }
        val row: Int = matrix.size
        val column: Int = matrix[0].size
        for (i in column - 1 downTo 0) {
            if (matrix[0][i] <= target) {
                for (j in 0 until row) {
                    if (matrix[j][i] == target) {
                        return true
                    }
                }
            }
        }
        return false
    }
}