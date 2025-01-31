import { useQuery, useMutation, useQueryClient } from 'react-query';
import { apiClient } from '../api/client';

export interface User {
    id: string;
    username: string;
    email: string;
    role: 'ADMIN' | 'TENANT' | 'MANAGER';
    status: 'ACTIVE' | 'INACTIVE';
    createdAt: string;
}

export const useUsers = () => {
    const queryClient = useQueryClient();

    const { data: users = [], isLoading, error } = useQuery<User[]>(
        'users',
        async () => {
            try {
                const response = await apiClient.get('/users');
                return response.data || [];
            } catch (error) {
                console.error('Error fetching users:', error);
                return [];
            }
        }
    );

    const addUser = useMutation(
        (newUser: Omit<User, 'id' | 'createdAt'>) =>
            apiClient.post('/users', newUser),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('users');
            },
        }
    );

    const updateUser = useMutation(
        (updatedUser: Partial<User> & { id: string }) =>
            apiClient.put(`/users/${updatedUser.id}`, updatedUser),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('users');
            },
        }
    );

    const deleteUser = useMutation(
        (userId: string) => apiClient.delete(`/users/${userId}`),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('users');
            },
        }
    );

    return {
        users,
        isLoading,
        error,
        addUser,
        updateUser,
        deleteUser,
    };
}; 