import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ThemeProvider, CssBaseline, AppBar, Toolbar, Typography } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { Dashboard } from './components/Dashboard';

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false,
            retry: 1,
        },
    },
});

const theme = createTheme({
    palette: {
        mode: 'light',
        primary: {
            main: '#1976d2',
        },
        secondary: {
            main: '#dc004e',
        },
    },
});

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6">
                            HMS Dashboard
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Dashboard />
            </ThemeProvider>
        </QueryClientProvider>
    );
}

export default App; 