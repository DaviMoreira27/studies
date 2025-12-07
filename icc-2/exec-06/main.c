#include <stdio.h>
#include <stdlib.h>

int solve_rec(int u, int cols, int *h_weights, int *v_weights, int *memo) {
    if (u == 0) {
        return 0;
    }

    if (memo[u] != -1) {
        return memo[u];
    }

    int max_friends = -1;

    int top_node = u - cols;
    if (top_node >= 0 && v_weights[top_node] != -1) {
        int res = solve_rec(top_node, cols, h_weights, v_weights, memo);
        if (res != -1) {
            int current_total = res + v_weights[top_node];
            if (current_total > max_friends) {
                max_friends = current_total;
            }
        }
    }

    if (u % cols != 0) {
        int left_node = u - 1;
        if (h_weights[left_node] != -1) {
            int res = solve_rec(left_node, cols, h_weights, v_weights, memo);
            if (res != -1) {
                int current_total = res + h_weights[left_node];
                if (current_total > max_friends) {
                    max_friends = current_total;
                }
            }
        }
    }

    memo[u] = max_friends;
    return max_friends;
}

int main(int argc, char *argv[]) {
    int rows, cols;
    if (scanf("%d %d", &rows, &cols) != 2) {
        return 0;
    }

    int num_nodes = rows * cols;

    int *memo = (int *)malloc(num_nodes * sizeof(int));
    int *h_weights = (int *)malloc(num_nodes * sizeof(int));
    int *v_weights = (int *)malloc(num_nodes * sizeof(int));

    for (int i = 0; i < num_nodes; i++) {
        memo[i] = -1;
        h_weights[i] = -1;
        v_weights[i] = -1;
    }

    int u, v, w;
    while (scanf("%d %d %d", &u, &v, &w) != EOF) {
        if (v == u + 1) {
            h_weights[u] = w;
        } else if (v == u + cols) {
            v_weights[u] = w;
        }
    }

    int destination = num_nodes - 1;
    int result = solve_rec(destination, cols, h_weights, v_weights, memo);

    printf("%d\n", result);

    free(memo);
    free(h_weights);
    free(v_weights);

    return 0;
}
