#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_READS 100
#define BUFFER_SIZE 1000

int calculate_overlap(char *s1, char *s2) {
    int len1 = strlen(s1);
    int len2 = strlen(s2);

    if (strstr(s1, s2) != NULL) {
        return len2;
    }

    if (strstr(s2, s1) != NULL) {
        return len1;
    }

    int min_len = (len1 < len2) ? len1 : len2;
    for (int k = min_len; k > 0; k--) {
        if (strncmp(s1 + len1 - k, s2, k) == 0) {
            return k;
        }
    }

    return 0;
}

char* merge_strings(char *s1, char *s2, int overlap) {
    if (strstr(s1, s2) != NULL) return strdup(s1);

    if (strstr(s2, s1) != NULL) return strdup(s2);

    int len1 = strlen(s1);
    int len2 = strlen(s2);
    int new_len = len1 + len2 - overlap;

    char *result = (char*)malloc((new_len + 1) * sizeof(char));
    if (!result) exit(1);

    strcpy(result, s1);
    strcat(result, s2 + overlap);

    return result;
}

int main() {
    int n;
    char *reads[MAX_READS];
    char buffer[BUFFER_SIZE];

    if (scanf("%d", &n) != 1) return 0;

    for (int i = 0; i < n; i++) {
        scanf("%s", buffer);
        reads[i] = strdup(buffer);
    }

    while (n > 1) {
        int max_overlap = -1;
        int best_i = -1;
        int best_j = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                int current_overlap = calculate_overlap(reads[i], reads[j]);

                if (current_overlap > max_overlap) {
                    max_overlap = current_overlap;
                    best_i = i;
                    best_j = j;
                }
            }
        }

        char *merged = merge_strings(reads[best_i], reads[best_j], max_overlap);

        char *temp_list[MAX_READS];
        int temp_idx = 1;

        temp_list[0] = merged;

        for (int k = 0; k < n; k++) {
            if (k != best_i && k != best_j) {
                temp_list[temp_idx++] = reads[k];
            } else {
                free(reads[k]);
            }
        }

        n--;
        for (int k = 0; k < n; k++) {
            reads[k] = temp_list[k];
        }
    }

    printf("%s\n", reads[0]);
    free(reads[0]);

    return 0;
}
