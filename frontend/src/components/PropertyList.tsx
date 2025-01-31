import React from 'react';
import {
    Box,
    Card,
    CardContent,
    Typography,
    Grid,
    CircularProgress,
    Alert,
} from '@mui/material';
import { useProperties } from '../hooks/useProperties';

export const PropertyList: React.FC = () => {
    const { properties, isLoading, error } = useProperties();

    if (isLoading) {
        return (
            <Box display="flex" justifyContent="center" p={4}>
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return (
            <Box p={4}>
                <Alert severity="error">
                    Error loading properties. Please try again later.
                </Alert>
            </Box>
        );
    }

    if (!properties.length) {
        return (
            <Box p={4}>
                <Alert severity="info">No properties available.</Alert>
            </Box>
        );
    }

    return (
        <Box p={4}>
            <Grid container spacing={3}>
                {properties.map((property) => (
                    <Grid item xs={12} sm={6} md={4} key={property.id}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6" gutterBottom>
                                    {property.name}
                                </Typography>
                                <Typography color="textSecondary" gutterBottom>
                                    {property.address}
                                </Typography>
                                <Typography variant="body2">
                                    Units: {property.availableUnits} / {property.totalUnits}
                                </Typography>
                                <Typography
                                    variant="body2"
                                    color={
                                        property.status === 'ACTIVE'
                                            ? 'success.main'
                                            : property.status === 'MAINTENANCE'
                                                ? 'warning.main'
                                                : 'error.main'
                                    }
                                >
                                    Status: {property.status}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}; 