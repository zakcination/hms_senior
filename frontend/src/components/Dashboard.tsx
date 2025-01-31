import React, { useState } from 'react';
import {
    Box,
    Container,
    Grid,
    Paper,
    Typography,
    Tab,
    Tabs,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    MenuItem,
    IconButton,
} from '@mui/material';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { useProperties } from '../hooks/useProperties';
import { useUsers, User } from '../hooks/useUsers';

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;
    return (
        <div hidden={value !== index} {...other}>
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}

export const Dashboard: React.FC = () => {
    const [tabValue, setTabValue] = useState(0);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [editingItem, setEditingItem] = useState<any>(null);
    const [formData, setFormData] = useState<any>({});

    const { users, isLoading: usersLoading, addUser, updateUser, deleteUser } = useUsers();
    const { properties, isLoading: propertiesLoading, addProperty, updateProperty, deleteProperty } = useProperties();

    const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        setTabValue(newValue);
    };

    const handleAdd = () => {
        setEditingItem(null);
        setFormData({});
        setIsDialogOpen(true);
    };

    const handleEdit = (item: any) => {
        setEditingItem(item);
        setFormData(item);
        setIsDialogOpen(true);
    };

    const handleDelete = async (item: any) => {
        if (window.confirm('Are you sure you want to delete this item?')) {
            if (tabValue === 0) {
                await deleteUser.mutateAsync(item.id);
            } else {
                await deleteProperty.mutateAsync(item.id);
            }
        }
    };

    const handleSubmit = async () => {
        if (tabValue === 0) {
            if (editingItem) {
                await updateUser.mutateAsync({ ...formData, id: editingItem.id });
            } else {
                await addUser.mutateAsync(formData);
            }
        } else {
            if (editingItem) {
                await updateProperty.mutateAsync({ ...formData, id: editingItem.id });
            } else {
                await addProperty.mutateAsync(formData);
            }
        }
        setIsDialogOpen(false);
    };

    const renderDialog = () => {
        const isUser = tabValue === 0;
        return (
            <Dialog open={isDialogOpen} onClose={() => setIsDialogOpen(false)}>
                <DialogTitle>{editingItem ? 'Edit' : 'Add'} {isUser ? 'User' : 'Property'}</DialogTitle>
                <DialogContent>
                    {isUser ? (
                        <>
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Username"
                                value={formData.username || ''}
                                onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                            />
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Email"
                                value={formData.email || ''}
                                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                            />
                            <TextField
                                select
                                fullWidth
                                margin="normal"
                                label="Role"
                                value={formData.role || ''}
                                onChange={(e) => setFormData({ ...formData, role: e.target.value })}
                            >
                                <MenuItem value="ADMIN">Admin</MenuItem>
                                <MenuItem value="TENANT">Tenant</MenuItem>
                                <MenuItem value="MANAGER">Manager</MenuItem>
                            </TextField>
                        </>
                    ) : (
                        <>
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Name"
                                value={formData.name || ''}
                                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                            />
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Address"
                                value={formData.address || ''}
                                onChange={(e) => setFormData({ ...formData, address: e.target.value })}
                            />
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Total Units"
                                type="number"
                                value={formData.totalUnits || ''}
                                onChange={(e) => setFormData({ ...formData, totalUnits: parseInt(e.target.value) })}
                            />
                            <TextField
                                fullWidth
                                margin="normal"
                                label="Available Units"
                                type="number"
                                value={formData.availableUnits || ''}
                                onChange={(e) => setFormData({ ...formData, availableUnits: parseInt(e.target.value) })}
                            />
                        </>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setIsDialogOpen(false)}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained" color="primary">
                        {editingItem ? 'Update' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        );
    };

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Paper sx={{ p: 2 }}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 2 }}>
                    <Tabs value={tabValue} onChange={handleTabChange}>
                        <Tab label="Users" />
                        <Tab label="Properties" />
                    </Tabs>
                </Box>

                <Box sx={{ mb: 2 }}>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<AddIcon />}
                        onClick={handleAdd}
                    >
                        Add {tabValue === 0 ? 'User' : 'Property'}
                    </Button>
                </Box>

                <TabPanel value={tabValue} index={0}>
                    {usersLoading ? (
                        <Typography>Loading users...</Typography>
                    ) : (
                        <Grid container spacing={2}>
                            {users.map((user) => (
                                <Grid item xs={12} key={user.id}>
                                    <Paper sx={{ p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                        <Box>
                                            <Typography variant="h6">{user.username}</Typography>
                                            <Typography color="textSecondary">{user.email}</Typography>
                                            <Typography>Role: {user.role}</Typography>
                                        </Box>
                                        <Box>
                                            <IconButton onClick={() => handleEdit(user)}>
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton onClick={() => handleDelete(user)}>
                                                <DeleteIcon />
                                            </IconButton>
                                        </Box>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>
                    )}
                </TabPanel>

                <TabPanel value={tabValue} index={1}>
                    {propertiesLoading ? (
                        <Typography>Loading properties...</Typography>
                    ) : (
                        <Grid container spacing={2}>
                            {properties.map((property) => (
                                <Grid item xs={12} sm={6} md={4} key={property.id}>
                                    <Paper sx={{ p: 2 }}>
                                        <Typography variant="h6">{property.name}</Typography>
                                        <Typography color="textSecondary">{property.address}</Typography>
                                        <Typography>
                                            Units: {property.availableUnits} / {property.totalUnits}
                                        </Typography>
                                        <Box sx={{ mt: 1 }}>
                                            <IconButton onClick={() => handleEdit(property)}>
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton onClick={() => handleDelete(property)}>
                                                <DeleteIcon />
                                            </IconButton>
                                        </Box>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>
                    )}
                </TabPanel>

                {renderDialog()}
            </Paper>
        </Container>
    );
}; 