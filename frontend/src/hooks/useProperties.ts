import { useQuery, useMutation, useQueryClient } from 'react-query';
import { apiClient } from '../api/client';

interface Property {
    id: string;
    name: string;
    address: string;
    totalUnits: number;
    availableUnits: number;
    status: 'ACTIVE' | 'INACTIVE' | 'MAINTENANCE';
}

export const useProperties = () => {
    const queryClient = useQueryClient();

    const { data: properties = [], isLoading, error } = useQuery<Property[]>(
        'properties',
        async () => {
            try {
                const response = await apiClient.get('/properties');
                return response.data || [];
            } catch (error) {
                console.error('Error fetching properties:', error);
                return [];
            }
        }
    );

    const addProperty = useMutation(
        (newProperty: Omit<Property, 'id'>) =>
            apiClient.post('/properties', newProperty),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('properties');
            },
        }
    );

    const updateProperty = useMutation(
        (updatedProperty: Property) =>
            apiClient.put(`/properties/${updatedProperty.id}`, updatedProperty),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('properties');
            },
        }
    );

    const deleteProperty = useMutation(
        (propertyId: string) => apiClient.delete(`/properties/${propertyId}`),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('properties');
            },
        }
    );

    return {
        properties,
        isLoading,
        error,
        addProperty,
        updateProperty,
        deleteProperty,
    };
}; 